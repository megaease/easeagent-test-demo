#!/bin/sh

curl -H "root_source:curl" http://127.0.0.1:8080/client
curl -H "root_source:curl" http://127.0.0.1:8080/client5
curl -H "root_source:curl" http://127.0.0.1:8080/client5_async
curl -H "root_source:curl" http://127.0.0.1:8080/okclient
curl -H "root_source:curl" http://127.0.0.1:8080/okclient_async