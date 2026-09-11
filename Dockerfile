FROM proxy-docker.nexus-ein.com.intraMyProject/openjdk:21-slim

LABEL Name="mediationplatform-opportunity-handler"
LABEL Description="APIs platform for application which want to communicate with CustomerLinks"
LABEL URL="https://gitlab.tech.MyProject/cl-platform"


ARG PROJECT_NAME=mediationplatform-opportunity-handler
ARG USER_NAME=mediationplatform
ENV PROJECT_DIR=/home/$PROJECT_NAME
ENV PROJECT=$PROJECT_NAME
ENV LOG_DIR=$PROJECT_DIR/files/logs
ENV TZ=Europe/Paris

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl libcurl4 tzdata && \
    groupadd -g 2001 0kogroup && \
    useradd -u 1001 -d $PROJECT_DIR -M -r -g 0kogroup $USER_NAME && \
    mkdir -p $LOG_DIR && \
    chown -R $USER_NAME:0kogroup $LOG_DIR && \
    chmod -R 777 $LOG_DIR && \
    cp /usr/share/zoneinfo/$TZ /etc/localtime && \
    echo $TZ > /etc/timezone && \
    rm -rf /var/lib/apt/lists/*


HEALTHCHECK --interval=5s --timeout=10s --retries=3 CMD curl -sS 127.0.0.1:8080 || exit 1

WORKDIR $PROJECT_DIR
COPY target/$PROJECT.jar $PROJECT.jar
USER $USER_NAME

ENTRYPOINT ["sh", "-c", "exec java -Dlog4j2.formatMsgNoLookups=true -Djavax.net.debug=ssl -Djavax.net.ssl.trustStore=$PROJECT_DIR/common/truststore.jks -Djavax.net.ssl.trustStorePassword=customerlinksplatform -jar $(ls $PROJECT_DIR/*.jar | head -n 1)"]

EXPOSE 8080
