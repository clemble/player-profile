FROM java:8-jre
MAINTAINER antono@clemble.com

EXPOSE 10012

ADD ./buildoutput/player-profile.jar /data/player-profile.jar

CMD java -jar -Dspring.profiles.active=cloud -Dlogging.config=classpath:logback.cloud.xml -Dserver.port=10012 /data/player-profile.jar
