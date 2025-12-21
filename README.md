# Broadcast-App PoC
Message multiple conversations simultaneously.
This sample Wire App is intended as a Proof of Concept to manage basic DB whitelisting and sending the message in multiple conversations.
It's not intended to be used in production as is, but rather as a starting point for building more complex Wire Apps.

### Prerequisites
Create a .env file in the project root as specified in [SDK documentation](https://dev.wire.com/quickstart-advanced#initializing-the-sdk)  
You can use the .env.sample file as a starting point and fill in data for the Wire user that will own the App.
The database environmental variables are already specified in postgres.env

### Usage
1. Start the App
```sh
  docker compose up
```

2. Authorize Wire user to broadcast.  
   Insert Wire ID + domain into the database:
```sh
  ./authorize_user.sh <user_id> <user_domain>
  # e.g. ./authorize_user.sh e514b71a-81d4-453f-84ec-a8a68b6804e2 wire.com
```

3. Add App to Conversations  
    Invite the App into any conversation you want to include in the broadcast.

4. Send a Broadcast
   In any conversation where the App is present, use:

```
/broadcast <your message here>
```

This will send the message to all other conversations where the App is added.
