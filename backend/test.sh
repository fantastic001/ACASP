#!/bin/sh

JS=""

function req() {
    method=$1
    path="$2"
    shift 2 
    data=""
    declare -A json
    while [ -n "$1" ]; do
        # echo "$1=$2"
        json["$1"]="$2"
        shift 2
    done
    keys="${!json[@]}"
    data="$(for key in $keys; do echo "\"$key\""; echo "\"${json[$key]}\""; done | jq -cn 'reduce inputs as $i ({}; . + { ($i): input })')"
    # echo $data
    if [ -z "$data" ]; then 
        curl -i -X $method -c cookies.txt -b cookies.txt -H "Content-Type: application/json" "localhost:5544/ACASPAPI-web/rest$path" 
    else
        curl -i -X $method -c cookies.txt -b cookies.txt -H "Content-Type: application/json" -d $data "localhost:5544/ACASPAPI-web/rest$path" 
    fi
}


function req2() {
    method=$1
    path="$2"
    shift 2 
    data=""
    declare -A json
    while [ -n "$1" ]; do
        # echo "$1=$2"
        json["$1"]="$2"
        shift 2
    done
    keys="${!json[@]}"
    data="$(for key in $keys; do echo "\"$key\""; echo "\"${json[$key]}\""; done | jq -cn 'reduce inputs as $i ({}; . + { ($i): input })')"
    # echo $data
    if [ -z "$data" ]; then 
        curl -i -X $method -c cookies2.txt -b cookies2.txt -H "Content-Type: application/json" "localhost:5546/ACASPAPI-web/rest$path" 
    else
        curl -i -X $method -c cookies2.txt -b cookies2.txt -H "Content-Type: application/json" -d $data "localhost:5546/ACASPAPI-web/rest$path" 
    fi
}

function expect() {
    name="$1"
    shift
    ($* > /dev/null 2>&1) && echo "[  OK  ] $name" || echo "[ FAIL ] $name"
}

req POST /users/register username stefan password sekrit
req2 POST /users/register username buba password sekrit
req2 POST /users/login username buba password sekrit
req POST /users/login username stefan password sekrit
req POST /messages/user toUsername buba fromUsername stefan subject hi text hi
req GET /messages
req2 GET /messages/
req2 POST /messages/user fromUsername buba toUsername stefan subject hi text Hello
