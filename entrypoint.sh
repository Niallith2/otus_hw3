#!/bin/sh
mv /otus_hw3/* .
mvn test -Dtest=$PROFILE -Dapi.baseUrl=$URL