FROM adoptopenjdk/openjdk11
ARG JAR_FILE=build/libs/gamjajeon.0.0.1.jar
ENV ACTIVE_PROFILE ${ACTIVE_PROFILE}
ENV ENV_PATH ${ENV_PATH}
COPY ${JAR_FILE} gamjajeon.0.0.1.jar
RUN echo java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -DENV_PATH="${ENV_PATH}" /gamjajeon.0.0.1.jar
CMD java -jar -Dspring.profiles.active=$ACTIVE_PROFILE -DENV_PATH="$ENV_PATH" /gamjajeon.0.0.1.jar