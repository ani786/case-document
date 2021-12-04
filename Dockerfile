FROM openjdk:15.0.1-jdk
VOLUME /tmp
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /lib
COPY ${DEPENDENCY}/META-INF /META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /
COPY ${DEPENDENCY}/BOOT-INF/classes/api /api
RUN chmod -R 777 /api
ENV TZ=Australia/Sydney
ENTRYPOINT ["java","-cp",":lib/*","com.mydocumentsref.api.portal.internal.casedocumentservice.CaseDocumentServiceApplication"]
