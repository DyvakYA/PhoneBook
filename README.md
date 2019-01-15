![chrome-capture 2](https://user-images.githubusercontent.com/20241892/51182270-4c14a480-18d6-11e9-9439-1818eba610ef.gif)



SET NAMES utf8 ;  
  
DROP TABLE IF EXISTS `contacts`;  
  
SET character_set_client = utf8mb4 ;  
  
DROP TABLE IF EXISTS `contacts`;  
SET character_set_client = utf8mb4 ;  
CREATE TABLE `contacts` (  
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,  
  `address` varchar(255) DEFAULT NULL,  
  `email` varchar(255) DEFAULT NULL,  
  `firstName` varchar(255) DEFAULT NULL,  
  `lastName` varchar(255) DEFAULT NULL,  
  `middleName` varchar(255) DEFAULT NULL,  
  `phoneNumberHome` varchar(255) DEFAULT NULL,  
  `phoneNumberMobile` varchar(255) DEFAULT NULL,  
  `userId` int(11) NOT NULL,  
  PRIMARY KEY (`id`),  
  KEY `FKpr6efdewumxxk85sxxvjvxtu3` (`userId`)  
) ENGINE=MyISAM AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;  
  
DROP TABLE IF EXISTS `users`;  
SET character_set_client = utf8mb4 ;  
CREATE TABLE `users` (  
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,  
  `username` varchar(45) DEFAULT NULL,  
  `password` varchar(45) DEFAULT NULL,  
  `fullName` varchar(45) DEFAULT NULL,  
  PRIMARY KEY (`id`)  
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;  