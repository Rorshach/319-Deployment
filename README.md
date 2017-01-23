# team-6

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

