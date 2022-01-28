#!/bin/sh

curl -H "root_source:curl" http://127.0.0.1:18888/feignclient
curl -H "root_source:curl" http://127.0.0.1:18888/web_client
curl -H "root_source:curl" http://127.0.0.1:18888/okclient
curl -H "root_source:curl" http://127.0.0.1:18888/okclient_async
curl -H "root_source:curl" http://127.0.0.1:18888/rest_template