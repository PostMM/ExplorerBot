ExplorerBot Discord Remote Terminal System
-Triple

Use Java 11+ to run this application.

1. To begin, open Discord developer portal and create a new bot.
Customize the name and image however you like.
2. Under the "bot" tab, create the bot. You will be provided with a password called a token. 
Save the token somewhere or write it down.
It is recommended to disable "public bot".
3. Enable the "message content" gateway intent.
4. Navigate to the URL generator and check "bot" under scopes.
Scroll to the bottom of the page and copy the URL.
5. Now using the URL, add a bot to a server, this is so that you can open
a direct message conversation with the bot.
6. Run the ExplorerBot application on the system you would like to access remotely.
7. On startup, navigate to "config" on the menu bar and select "enter token".
8. Enter the previously saved token. Then enter your discord user ID.
Make sure this is your 18 digit ID and not your username.
9. Restart the application.
10. All done. Now simply DM "help" to your bot on discord to begin.



Use commands to navigate your file system and download files remotely:
clear: clear messages from ExplorerBot
ld: list directories current directory
ls: list files in current directory
cd: [dir]: change to specified directory
pd: return to parent directory
sd: [path]: jump to specified directory
get: [file]: request the specified file (limit 8MB) to download