package com.example.drone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ros.address.InetAddressFactory;
import org.ros.android.BitmapFromCompressedImage;
import org.ros.android.RosActivity;
import org.ros.android.view.RosImageView;
import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.ConnectedNode;
import org.ros.node.Node;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;
import org.ros.node.topic.Publisher;

import std_msgs.Int64MultiArray;

public class MainActivity extends RosActivity {

    public String value_tall=null;
    public String  right_state=null;//右邊搖桿狀態(傳送過去)
    public String  left_state=null;//左邊搖桿狀態(傳送過去)
    public int flag=0;//按鈕狀態標記，0上鎖，1解鎖
    public long lock_state=100;//operation_1解鎖(傳送過去)
    public long a_state=0;///////////////////////////////////////測試用
    public long long_mode=2;
    public int flag2=0;
    public int flag3=0;
    public String flag66="0";
    public float left_x=0;
    public float left_y=0;
    public float right_x=0;
    public float right_y=0;
    private RosImageView<sensor_msgs.CompressedImage> image;

    public MainActivity() {
        //super("ros_test", "ros_test", URI.create("http://192.168.1.9:11311")); // 這裡是ROS_MASTER_URI
        super("ImageTransportTutorial", "ImageTransportTutorial");
    }
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button menu_button =(Button)findViewById(R.id.menu);
        final TextView t =(TextView) findViewById(R.id.test);
        final TextView t1 =(TextView) findViewById(R.id.tet);
        final Rocker rockerViewLeft = (Rocker) findViewById(R.id.rocker_right);
        final Joystick rockerViewRight = (Joystick) findViewById(R.id.rocker_left);
        final Button lk=(Button)findViewById(R.id.lock);
        final Button mode_cancel=(Button)findViewById(R.id.mode_cancel);
        final Button turn=(Button)findViewById(R.id.turnback);
        final TextView xxx=(TextView) findViewById(R.id.right_x);
        final TextView yyy=(TextView) findViewById(R.id.right_y);
        final TextView mTvAngle = (TextView) findViewById(R.id.tv_now_angle);
        final TextView xxxxx=(TextView)findViewById(R.id.left_x);
        final TextView yyyyy=(TextView)findViewById(R.id.left_y);
        TextView texttall=(TextView)findViewById(R.id.texttall);////////////傳值測試
        image = (RosImageView<sensor_msgs.CompressedImage>) findViewById(R.id.image);
        image.setTopicName("camera/image_raw/compressed");
        image.setMessageType(sensor_msgs.CompressedImage._TYPE);///CompressedImage
        image.setMessageToBitmapCallable(new BitmapFromCompressedImage());

        Bundle bundle=getIntent().getExtras();
        String strtall=bundle.getString("user_tall");
        texttall.setText(strtall);
        value_tall=strtall;
        /**抓取模式傳值*/
        Intent intent = getIntent();
        String mode_state = intent.getStringExtra("mode_state");
        TextView ipp=(TextView)findViewById(R.id.textip);


        rockerViewRight.setOnGetXYDistance(new Joystick.OnGetXXYYDistance() {
            @Override
            public void getx(float xxxx) {
                right_x=xxxx;
                xxx.setText(Float.toString(xxxx));
            }

            @Override
            public void gety(float yyyy) {
                right_y=-yyyy;
                yyy.setText(Float.toString(-yyyy));
            }
        });

        rockerViewLeft.setOnGetXYDistance(new Rocker.OnGetXYDistance() {
        @Override
        public void getx(float xx) {

              left_x=xx;
              xxxxx.setText(Float.toString(xx));
        }

        @Override
        public void gety(float yy) {
              left_y=-yy;
              if(Float.toString(-yy) =="-0.0") {
                  yyyyy.setText("0");
              }
              else
              { yyyyy.setText(Float.toString(-yy));}
        }
        });
        rockerViewLeft.setOnAngleChangeListener(new Rocker.OnAngleChangeListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void angle(double angle) {
                mTvAngle.setText("當前角度："+angle);
            }

            @Override
            public void onFinish() {

            }
        });
        /**模式選擇*/
        if("1".equals(mode_state)){
            ipp.setText("mode:Stablize");
            long_mode=1;
        }
        else if ("2".equals(mode_state)){
            ipp.setText("mode:Loiter");
            long_mode=2;
        }
        else if ("3".equals(mode_state)){
            ipp.setText("mode:Circle");
            long_mode=3;
        }
        else if ("4".equals(mode_state)){
            ipp.setText("mode:Land");
            long_mode=4;
        }
        else if ("5".equals(mode_state)){
            ipp.setText("mode:Follow");
            long_mode=5;
        }
        mode_cancel.setOnClickListener(new Button.OnClickListener() {//////////////////取消模式
            @Override
            public void onClick(View v) {
                long_mode=6;
            }
        });

        lk.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==0)
                { flag++;
                    lock_state=1;}//解鎖
                else if (flag==1)
                { flag=0;
                    lock_state=0;}//鎖上
            }
        });

        menu_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this , Menu.class);
                startActivity(intent1);
            }
        });
        turn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent();
                intent3.setClass(MainActivity.this,input.class);
                startActivity(intent3);
            }
        });
    }
    @Override
    protected void init(NodeMainExecutor nodeMainExecutor) {
        NodeConfiguration nodeConfiguration1 = NodeConfiguration.newPublic(InetAddressFactory.newNonLoopback().getHostAddress(), getMasterUri());
        nodeMainExecutor.execute(image, nodeConfiguration1.setNodeName("android/video_view"));
        NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(getRosHostname());
        nodeConfiguration.setMasterUri(getMasterUri());
       nodeMainExecutor.execute(new NodeMain() {
            @Override
            public GraphName getDefaultNodeName() {
                return GraphName.of("ros_test");
            }
            @Override
            public void onStart(ConnectedNode connectedNode ) {

                final Publisher<Int64MultiArray> pub =  connectedNode.newPublisher("test", std_msgs.Int64MultiArray._TYPE);
                connectedNode.executeCancellableLoop(new CancellableLoop() {
                    @Override
                    protected void loop() throws InterruptedException {
                        std_msgs.Int64MultiArray msg = pub.newMessage();
                        long rocker_left_x=Float.valueOf(left_x).longValue();
                        long rocker_left_y=Float.valueOf(left_y).longValue();
                        long rocker_right_x=Float.valueOf(right_x).longValue();
                        long rocker_right_y=Float.valueOf(right_y).longValue();
                        long[] operation= {lock_state,long_mode,rocker_right_x,rocker_left_y,rocker_right_y,rocker_left_x};
                        msg.setData(operation);
                        pub.publish(msg);
                        lock_state=100;
                        Thread.sleep(750);
                        /**
                         * (1)lock_state:解鎖
                         * (2)long_mode:模式
                         * (3)rocker_right_x:roll
                         * (4)rocker_left_y:pitch
                         * (5)rocker_right_y:throttle
                         * (6)rocker_left_x:yaw
                         */
                    }
                });
            }
           /* @Override
            public void onShutdown(Node node) {
            }*/
            /*@Override////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////嘗試改成lock傳Bool
            public void onStart(ConnectedNode connectedNode) {
                final Publisher<std_msgs.Bool> pub =  connectedNode.newPublisher("test", std_msgs.Bool._TYPE);
                connectedNode.executeCancellableLoop(new CancellableLoop() {
                    @Override
                    protected void loop() throws InterruptedException {
                        std_msgs.Bool msg = pub.newMessage();

                        msg.setData(left_state+"   "+right_state+"   "+button_state);
                        pub.publish(msg);
                        Thread.sleep(3000);
                    }
                });
            }*/
            @Override
            public void onShutdown(Node node) {

            }
            @Override
            public void onShutdownComplete(Node node) {

            }
            @Override
            public void onError(Node node, Throwable throwable) {

            }
        }, nodeConfiguration);
    }
    /*private String getMyIp(){
        WifiManager wifi_service = (WifiManager)getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifi_service.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = String.format("%d.%d.%d.%d",(ipAddress & 0xff),(ipAddress >> 8 & 0xff),(ipAddress >> 16 & 0xff),(ipAddress >> 24 & 0xff));
        return ip;
    }*/
}


