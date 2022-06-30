#!/bin/bash
#===============================================================================
#
#          FILE:  
# 
#         USAGE:   
# 
#   DESCRIPTION:  This script is used to transfer CO customer/member.dat files to s3
#       OPTIONS:  ---
#  REQUIREMENTS:  This Script Requires s3 cmd Utility. 
#          BUGS:  ---
#         NOTES:  ---
#       COMPANY:  
#       VERSION:  1.1
#       CREATED:  4/01/2021 <--- i aint no fool
#      REVISION:  ---
#===============================================================================
################################################################################
##Local Directory For downloading files from S3 Bucket            
################################################################################
MEMBER="COACHID~COACHID~PARENTCOACH~PARENTCOACH~FIRST~LAST~~(928) 388-5995~~~~3639 S Salida del Sol Ave~~~~Yuma~AZ~85365~EMAILHERE~~~840~10/21/2021~RANKID~RANKID~~Inactive~RANKID~LEG~SPONSOR~3639 S Salida del Sol Ave~~~~Yuma~AZ~85365~~~~Mail US Physical Check~N~No~en_US~GUID~~~~10/21/2021~10/21/2021~~~~10/21/2021~YES~10/21/2021~"

CUSTOMER="CUSTOMER~SPONSOR~FIRST~LAST~(678) 427-8687~~~~228 Wisso Rd~~~~Griffin~GA~30223~EMAILHERE~~~840~01/08/2021~NO~Club~My Site~228 Wisso Rd~~~~Griffin~GA~30223~~2649003~0~Y~en_US~Unrestricted~0~GUID~"

##Local Directory For downloading files from S3 Bucket            
LOCAL_DIR=data/

##Where to Log your activities during tranasfer           
LOG_FILE=scriptlog

##Target location to extract the ZIP file      
ZIP_EXT_DIR=data
      
##Admin Email For sending Log
ADMIN_EMAIL=snelson@test.com

##Email Subject                 
EMAIL_SUBJECT="S3 Transfer Status On"  #Should be in Quotes     
#*****************************************************************************************
PATH=/usr/local/bin:/usr/bin:/bin:
DATE=$(date +"%d-%m-%Y_%Hh:%Mm")
TIME=$(date +"%Hh:%Mm")
echo "" >>$LOG_FILE
echo "$DATE" >>$LOG_FILE
#
[ -d $LOCAL_DIR ] || mkdir -p $LOCAL_DIR
[ -d $ZIP_EXT_DIR ] || mkdir -p $ZIP_EXT_DIR
#
#Main Functions
#This function is used to extract current sessions log from log file and send it to Admin
#mail_me () {
 #LIVE=$(cat $LOG_FILE | grep -n "$DATE" | tail -1 | cut -d":" -f 1)
 #tail -n +$LIVE $LOG_FILE | mail -s "$EMAIL_SUBJECT $DATE" $ADMIN_EMAIL
#}
#
#This function will send mail then exit with status 0 
#exit_0 () {
  #mail_me
 # exit 0
#}
#
#This function will send mail then exit with status 1 (End up with error!)
#exit_1 () {
  #mail_me
 # exit 1
#}
#########################
## Get the Last uploaded File-name from S3 Bucket (Only One)
#S3_OBJECT=$(s3cmd ls s3://$S3_BUCKET | sort | tail -1 | awk '{ print $4 }') 
## Download the latest file into Local Directory
#s3cmd get $S3_OBJECT $LOCAL_DIR 2>>$LOG_FILE                              
#aws s3 ls s3://bb-coo-reports-ingest-store-uat
#aws s3 ls s3://$S3_BUCKET 
##Your S3 Bucket Name
if [ $# -eq 1 ]; then
    echo "one argument supplied"
    #IN=$DATA
    #echo $IN
fi

S3_BUCKET=$1
if [ $# -eq 2 ]
  then
    echo "two arguments supplied"
    IN=$2
fi
echo Bucket Name: $S3_BUCKET
echo In: $IN
echo Data: $DATA
echo Bucket: $BUCKET
arr=(${IN//\:/ })
echo ${arr[@]}

FILE=${arr[0]} 

if [[ $arr[1] =~ "customer" ]]
then 
	echo "customer dat found" 
	DATFILE=$CUSTOMER
	PAT=${arr[1]}
	#OAecho $PAT
	DATFILE=${DATFILE/CUSTOMER/$PAT}
	DATFILE=${DATFILE/SPONSOR/${arr[2]}}
	echo $DATFILE 

else 
	DATFILE=$MEMBER 
	DATFILE=${DATFILE/COACHID/${arr[1]}}
	DATFILE=${DATFILE/COACHID/${arr[1]}}
	#### there are two calls need for the above and below params 
	DATFILE=${DATFILE/PARENTCOACH/${arr[2]}}
	DATFILE=${DATFILE/PARENTCOACH/${arr[2]}}
	DATFILE=${DATFILE/LEG/${arr[7]}}
	DATFILE=${DATFILE/SPONSOR/${arr[8]}}
	DATFILE=${DATFILE/RANKID/${arr[9]}}
	DATFILE=${DATFILE/RANKID/${arr[9]}}
	DATFILE=${DATFILE/RANKID/${arr[9]}}
fi 
DATFILE=${DATFILE/FIRST/${arr[3]}}
DATFILE=${DATFILE/LAST/${arr[4]}}
DATFILE=${DATFILE/EMAILHERE/${arr[5]}}
#DATFILE=${DATFILE/PARENTCOACH/${arr[5]}}
#DATFILE=${DATFILE/LEG/${arr[5]}}
DATFILE=${DATFILE/GUID/${arr[6]}}

echo $DATFILE 

# remove the frist char because it's separator
FILE=data/$FILE
echo $FILE

echo $DATFILE > $FILE
cat $FILE 
echo "aws s3 cp $FILE s3://$S3_BUCKET"  
aws s3 cp $FILE s3://$S3_BUCKET 
#########################
if [ $? -eq 0 ]
  then 
      echo "$TIME : Transfer Completed Successfully" >>$LOG_FILE
 #     exit_0   
  else
      echo "$TIME : Transfer Failed with Above Errors" >>$LOG_FILE
  #    exit_1
fi
#########END#######
# remove the frist char because it's separator
