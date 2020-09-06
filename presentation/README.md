# Layered JAR Presentation

The presentation is prepared with [backslide](https://www.npmjs.com/package/backslide).  

1. Backslide requires [node](https://nodejs.org/) installed.

   ```bash
   $ node –version
   ```

   This presentation was prepared with node 10

   ```bash
   v10.22.0
   ```

   Node can be installed using [brew]()

   ```bash
   $ brew install node@10
   ```

   or upgraded if needed

   ```bash
   $ brew upgrade node@10
   ```

1. Install the dependencies

   ```bash
   $ npm install
   ```

1. Start the presentation

   ```bash
   $ npm start
   ```

   This will start a server from where the slides can be accessed.

   ```bash
   > presentation-layered-jar@1.0.0 start presentation-layered-jar/presentation
   > ./node_modules/backslide/bin/bs serve

   [Browsersync] Access URLs:
    ------------------------------------
      Local: http://localhost:4100
      External: http://192.168.178.35:4100
    ------------------------------------
   [Browsersync] Serving files from: .tmp
   [Browsersync] Serving files from: template
   [Browsersync] Serving files from: .
   [Browsersync] Watching files...
   ```

   No need to restart as changes are loaded automatically.

1. Export the presentation as PDF

   ```bash
   $ npm run pdf
   ```
