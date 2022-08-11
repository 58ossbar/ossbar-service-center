FROM frolvlad/alpine-oraclejdk8:slim
USER root
VOLUME /tmp
ADD creatorblue-platform-core.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-server -Xms256M -Xmx512M -XX:MetaspaceSize=64M -XX:MaxMetaspaceSize=128M"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.net.preferIPv4Stack=true -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]