/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {HelloWorld} from './src/HelloWorld';
import {name as appName} from './app.json';

AppRegistry.registerComponent('bridingNative', () => HelloWorld);
