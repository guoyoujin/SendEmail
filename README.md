# SendEmail
这是通过手机自动发送邮件的功能，出品原因是因为某人有一个excel表格，里面有几十万个邮箱，他每天需要发送一千封邮件，重复性太强，特为他定制开发了这么个发邮件的app
使用的是apache的poi
只需要提供excel文件即可，支持2007和2003的excel格式文件

* excel文件格式介绍

firstName | lastName | email | coutry | address | sendState
----------|----------|-------|--------|---------|----------
guo | youjin | test@qq.com | china | bejing | 0
guo | youjin | test@qq.com | china | bejing | 0
guo | youjin | test@qq.com | china | bejing | 0
guo | youjin | test@qq.com | china | bejing | 0



用的时候excel表格按照上面的来即可，记住第三列一定要是email，第五列不要占用，留空即可，具体样子可以看根目录下的excel文件：测试123.xlsx


