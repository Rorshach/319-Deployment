# team-6
<h3>Current Version Status:</h3>

[![Build Status](https://travis-ci.org/UBC-CPSC319/team-6.svg?branch=master)](https://travis-ci.org/UBC-CPSC319/team-6)

<h1> Setup </h1>

<h2> Front-end setup </h2>

Please first install npm to perform the setup.


(sudo) npm install
- This will install all of our components. The remaining global calls will ensure you have terminal commands for
development purposes.

Probably useful to global install:

npm install -g babel-cli

npm install -g webpack-dev-server


In terminal, run 'webpack' to compile a new bundle.js file.
Optionally we can also run a webpack dev server that performs live recompile updates.

Please ensure you've installed this globally via: npm install webpack-dev-server -g


When adding JavaScript files make sure to add them into the entry.js imports.


<h2> Front-end development </h2>
I've set up our front-end to accept ES6 and compile down to ES5 in order to ensure we satisfy our IE11 browser issues.

<h2> Prior to pushing </h2>

(1) Run webpack and ensure your bundle is up to date.
(2) Run tests.

<h2> Automated User Interface Browser Testing Setup (NightwatchJS) </h2>

First, install NightwatchJS with npm.
- npm install -g nightwatch

Next, download the latest Selenium standalone server from http://selenium-release.storage.googleapis.com/index.html which would be the selenium-server-standalone-3.0.1.jar

Go into the nightwatch folder and create a folder named "bin" and put this Selenium server in it.

*NOTE*: Right now the default browser that the tests will run on is Firefox. Configuration for IE11 is coming soon.

For more information on the setup: http://nightwatchjs.org/guide

<h3> Executing NightwatchJS Scripts </h3>

If you are currently in the nightwatch folder, run the following in your terminal
- nightwatch tests/test.js 

(This will execute the test.js script in the tests folder)

For more information on how to execute test scripts, there is documentation on the NightwatchJS site: http://nightwatchjs.org/guide#running-tests

<h3> Writing NightwatchJS Scripts </h3>

NightwatchJS has a nice API documenting how to use their functions (and how to create your own): http://nightwatchjs.org/api

A couple of additional good links are learning how to read xpaths and CSS selectors (you can use either in NightwatchJS):
- http://www.w3schools.com/xml/xpath_intro.asp
- http://www.w3schools.com/cssref/css_selectors.asp

You can usually use tools like Firebug on Firefox, or use the developer tools on Chrome to help find these selectors for automated browser testing.
