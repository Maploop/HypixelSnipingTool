# HypixelSnipingTool

How to use:
1. Download the artifact from releases page
2. Create a file named start.bat in the same folder as the jar file
3. Use the java -jar <FileName> command in your .bat file to run the program
4. Configure the program as you wish


Configuration guide:
```json
{
  "token": "",
  "channel-id": "",
  "api-key": "",
  "checks": [
    "46c39dc043084be09bbb348a40476280"
  ]
}
```

Token: discord bot token
channel-id: the id of the channel that messages will go into
api-key: a player's api key
checks: a list of UUIDs that are going to be checked every second
