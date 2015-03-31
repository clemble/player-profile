FROM java:8-jre
MAINTAINER antono@clemble.com

EXPOSE 8080

ADD target/player-profile-0.17.0-SNAPSHOT.jar /data/player-profile.jar

CMD java -jar -Dspring.profiles.active=cloud /data/player-profile.jar
