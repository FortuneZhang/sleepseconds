#!/usr/bash
#user fortunezhang
#email zhangfortune527@gmail.com
# 使用须知
#a.此文件和AndroidManifest.xml在同一个目录下
#b.启动activity 必须为MainActivity
#c.MainActivity必须在项目名下（com.google.android），或者在项目名的activities文件夹下面


echo "\033[32;49;41m auto running\033[39;49;0m"
#收集信息
#包名
line=`grep -r 'package=\"com' AndroidManifest.xml | head -1 `
s=${line#*\"};
package_name=${s%%\"*}

#app名
eval grep  'android:name=\".activities.MainActivity\"' AndroidManifest.xml
if [ $? -eq  0 ];then
    app_name="$package_name"".activities.MainActivity"
else
    app_name="$package_name"".MainActivity"
fi

#apk名
apk_name=${package_name##*\.}
debug_name='sleepseconds-debug.apk'

#清楚
echo "\033[32;49;11m clean apk ... \033[39;49;0m"
adb clean

#卸载手机上已经存在的app，如果不需要卸载注释下面就可以了，前面加#
echo "\033[32;49;1m uninstall apk ... \033[39;49;0m"
(adb uninstall $package_name)

#尝试debug，出现错误会显示红色字样
echo "\033[32;49;1m debug ...  \033[39;49;0m"
ant debug
if [ $? -eq 1 ]; then
    echo "\033[32;49;31m failed  \033[39;49;0m"
    return ;
fi

#安装到手机
echo "\033[32;49;1m debug success and ready to install apk  \033[39;49;0m"
adb install -r bin/$debug_name
echo "\033[32;49;1m install success ready start it  \033[39;49;0m"

#启动app
echo "\033[32;49;1m start activity   \033[39;49;0m"
adb shell am start -n $package_name/$app_name
echo "\033[32;49;1m start activity success then see your phone \033[39;49;0m"


