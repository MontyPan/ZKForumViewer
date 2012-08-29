Viewer of [ZK Forum](http://www.zkoss.org/forum/)

> This project is an UI demo project. 
> The forum data is fetched by rough HTML parser and saved without concern.
> Please don't use it in real production.

# [Download WAR file](https://github.com/downloads/MontyPan/ZKForumViewer/ZKForumViewer-6.5.0.FL.20120815.war)
______________________________________________________________________

Goal
====
* Write once, run on both tablet and desktop.
* Pure ZK component.
* As less custom CSS as possible.

Environment :
* JDK 1.6
* ZK 6.5 EE
* Desktop: Chrome, Firefox, Safari, Opera
	* Few flaws in IE9, terrible in IE7  :(
* Tablet: iPad, Android

Version
=======
## 2012.08.2x ##
special thank: [Vincent Jian](https://github.com/VincentJian)

## 2012.08.17 ##
* Add mock mode
	* In index.zul, use `@init("org.zkoss.demo.tablet.vm.ThreadViewModel", mode="mock")` 
	  will fetch static data.
* Refactory CSS, fix UI on Firefox.

## 2012.07.18 ##
Release for Demo.