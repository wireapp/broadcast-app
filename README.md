# Broadcast-App PoC
Message multiple conversations simultaneously.

### Prerequisites
Create a .env file in the project root as specified in [SDK documentation](https://github.com/wireapp/wire-apps-jvm-sdk/blob/main/docs/APPLICATION.md#initializing-the-sdk)  
The database environmental variables are specified in postgres.env

### Usage
1. Start the App
```sh
  docker compose up
```

2. Authorize broadcaster Wire user.  
   Insert Wire ID + domain into the database:
```sh
  ./authorize_user.sh <user_id> <user_domain>
  # e.g. ./authorize_user.sh e514b71a-81d4-453f-84ec-a8a68b6804e2 wire.com
```

3. Add App to Conversations  
    Invite the app into any conversation you want to include in the broadcast.

4. Send a Broadcast
   In any conversation where the app is present, use:

```
/broadcast <your message here>
```

This will send the message to all other conversations where the app is added.
