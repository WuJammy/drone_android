# 智慧跟拍無人機(Android APP)
## (一)專案目的
現代自媒體興起，有許多人開始拿起相機、攝影機或手機，利用影像記錄自已的生活，保存下各種美好回憶。而在這之中，我們觀察到有一些影像紀錄者，習慣從事戶外運動並進行拍攝記錄(例如:馬拉松、攀岩等等)，而往往他們在拍攝這類影片時，通常僅能利用手持的方式錄影，又或者要耗費大量人力及設備才能進行拍攝，但這樣除了不方便外，也容易造成拍攝的影像模糊且不精確，錯失了各種精采畫面的瞬間。為了解決這個問題，我們想到可以讓無人機自動去鎖定
被拍攝者進行跟拍的動作，而被拍攝者只需要一個人，利用手機APP，簡單的一個點擊，輕輕鬆鬆就能完成拍攝。達到讓使用者完全不用操心整個拍攝過程，將一切拍攝任務放心的交給無人機，使用者只需好好享受當下的運動過程，就可以很輕鬆得到一個穩定且漂亮的影像。
## (二)系統架構及流程
我們在無人機上搭載了NVIDIA Jetson TX2，其主要是用來搭配OpenCV及Yolo進行影像辨識(辨識人)以及操控Pixhawk這塊飛控板，當無人機辨識且鎖定到被拍攝者，便可利用Pixhawk來控制無人機飛行，實現自動跟拍，同時為了讓拍攝影像更穩定，我們也在無人機上搭載了鏡頭雲台，使鏡頭不會受到劇烈晃動。於安全性上，我們透過超音波感測器，讓其可自動避開障礙物，且當電池電量過低時，無人機便會自動緊急下降著陸。而我們在手機的APP上，將無人機及手機連到同一個Wifi後，透過ROS，藉此實現讓使用者只需用手機便可選擇動操作無人機或自動跟拍，並可在APP上顯示電池電量、Wifi訊號強度和當前的模式，同時將無人機的影像即時傳到手機畫面上，且可讓使用者選擇拍照或錄影模式，記錄下屬於當下美好的影像。

<div align=center>
<img  src=https://github.com/WuJammy/drone_android/blob/master/image/struct.png/>
</div>
<p align="center">圖1: 簡易架構圖</p>

<div align=center>
<img  src=https://github.com/WuJammy/drone_android/blob/master/image/flow.png/>
</div>
<p align="center">圖2: 簡易流程圖</p>

## (三)無人機的硬體設備
(1) NVIDIA Jetson TX2 <br>
(2) Pixhawk <br>
(3) 超音波感測器 <br>
(4) 鏡頭雲台及鏡頭<br>
(5) 電壓電流傳感器<br>
## (四)手機APP介面
### (1) 操作介面
<div align=center> <img  src=https://github.com/WuJammy/drone_android/blob/master/image/main_screen.png/> </div>
<p align="center">主畫面</p>

功能解釋: <br>
(1) 解鎖: 安全設定必需點擊後才能操作無人機<br>
(2) 左搖桿: 控制無人機方向<br>
(3) 右搖桿: 控制無人機的加速及減速<br>
(4) 選單圖示: 進入六種模式選單<br>
(5) 快門圖示: 長按錄影和短按拍照<br>

<div align=center> <img  src=https://github.com/WuJammy/drone_android/blob/master/image/menu_screen.png/> </div>
<p align="center">六種模式選單</p>

功能解釋: <br>
(1) Loiter: 將無人機維持在固定高度<br>
(2) Circle: 環繞著被拍攝者拍攝<br>
(3) Stablize: 維持當前狀態<br>
(4) Land: 自動著陸<br>
(5) Follow: 自動跟拍<br>
(6) Back: 返回出發位置<br>

### (2) 開發工具及使用的技術
 [1. Android Studio](https://developer.android.com/studio)  <br> 
 [2. ROS-Android](http://wiki.ros.org/android)  <br> 
 
 
### (3) 於Android APP中使用ROS(JAVA)
- 開發APP時，可不必於ROS的環境中<br> 
- 這裡僅介紹簡易基礎用法，詳細請參考[ROS-Android-Tutorials](http://wiki.ros.org/android/Tutorials)

#### 1. 將ROS導入Android APP
首先將build.gradle內特定程式碼進行替換，如下:
```gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```
將其替換成:
```gradle
buildscript {
  apply from: "https://github.com/rosjava/android_core/raw/kinetic/buildscript.gradle"
}
```
並加入
```gradle
subprojects {
    apply plugin: 'ros-android'

    afterEvaluate { project ->
        android {
            // Exclude a few files that are duplicated across our dependencies and
            // prevent packaging Android applications.
            packagingOptions {
                exclude "META-INF/LICENSE.txt"
                exclude "META-INF/NOTICE.txt"
            }
        }
    }
}
```

導入後有任何問題，請參考[Installation - Android Studio Development](http://wiki.ros.org/android/Tutorials/kinetic/Installation%20-%20Android%20Studio%20Development%20Environment)，進行問題排解

#### 2. 於AndroidManifest.xml添加權限
```xml
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.WAKE_LOCK"/>
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
```

#### 3. MainActivity繼承RosActivity 
```java
public class MainActivity extends RosActivity {...}
```
#### 4. 連接的節點名稱及位置
```java
public MainActivity() {
        super("ImageTransportTutorial", "ImageTransportTutorial");
    }
```
#### 5. 初始化及創建(連接)節點
- 此節點程式碼為進階的寫法，基本的使用請參考[Creating nodes](http://rosjava.github.io/rosjava_core/latest/getting_started.html#creating-nodes)

```java
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

               connectedNode.executeCancellableLoop(new CancellableLoop() {...});
            }
          
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
```
#### 6. Pubisher
```java
     final Publisher<Int64MultiArray> pub =  connectedNode.newPublisher("test", std_msgs.Int64MultiArray._TYPE);
     connectedNode.executeCancellableLoop(new CancellableLoop() {
         @Override
         protected void loop() throws InterruptedException {
              std_msgs.Int64MultiArray msg = pub.newMessage();
              ....
              }});
```

#### 5.Listener 







