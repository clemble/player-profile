FROM java:8-jre
MAINTAINER antono@clemble.com

EXPOSE 10012

ADD target/player-profile-*-SNAPSHOT.jar /data/player-profile.jar

CMD java -jar -Dspring.profiles.active=cloud -Dserver.port=10012 /data/player-profile.jar
