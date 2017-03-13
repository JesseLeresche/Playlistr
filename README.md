# Playlistr

This application is intended to be an enhacement to Deezer (as well as other music streaming services one day) by allowing bulk editing of playlists.

## Disclaimer

This is still a work in progress. Although functional, there is still some work that needs to be done. ONce complete, there will be a tutorial on how to implement your own version of this

## Features

- Creating new Playlists
- Deleting Playlists
- Bulk adding of songs to Playlists
- Bulk removing songs from Playlists
- Support for many Playlist file uploads (Future)

Other future enhancements include allowing support for multiple streaming services, such as Google Play music, Spotify, etc.

## Instructions for getting the application running

- Go to Deezer Developer Site [http://developers.deezer.com]
- Create a new application
- Check out source code
- Rename test.application.yml to application.yml
- Paste application id and secrect into the client id and secret fields
- Compile the application with maven using mvn clean install
- Start up the application with java -jar target/playlistr-0.0.1-SNAPSHOT.jar
- Open a web browser and Navigate to localhost:9000
- You should see the application homescreen
