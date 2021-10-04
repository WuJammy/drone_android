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
(1)解鎖: <br>
(2)左搖桿: <br>
(3)右搖桿:<br>

<div align=center> <img  src=https://github.com/WuJammy/drone_android/blob/master/image/menu_screen.png/> </div>
<p align="center">六種模式選單</p>

### (2) 開發工具及使用的技術
 [1. Android Studio](https://developer.android.com/studio)  <br> 
 [2. ROS](http://wiki.ros.org/android)  <br> 
 
