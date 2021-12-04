#!/usr/bin/perl
#============================================================
# Run the Standard Transaction
#============================================================
$ENV{"API_CLIENT_CODE"} = "webcard";
$ENV{"API_PORT_NUMBER"} = "20302";
$ENV{"REMOTE_USER"} = "lpi";
$ENV{"API_REQUEST"} = "RT|depopd|1863/1000001|N";
#$ENV{"API_REQUEST"} = "DR|depopd|DL|aa1|||E||";
#$ENV{"API_REQUEST"} = "DS|DL|aa1";


$result = `/api/webexes/webapi`;


# $result = `/api/webexes/webapi "RT|websphere test|1863/1000001|N"`;


print "$result";
