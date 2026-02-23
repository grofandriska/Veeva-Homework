# Veeva-Homework
API calls to Veeva vault API

Endpoints:
- /account/registration


Request: 
- POST 
- BODY {
  "first_name__c" : "Harry",
  "last_name__c" :"Potter" ,
  "email__c" : "harryPotter@example.com"
  }

Response:
- BODY {
  "statusCode" : "200",
  "vaultId" : "4482"
  }
