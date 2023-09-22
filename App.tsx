/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useState} from 'react';
import type {PropsWithChildren} from 'react';
import {
  Button,
  SafeAreaView,
  ScrollView,
  ScrollViewComponent,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import {PersonView} from './src/PersonView';

function App(): JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  type Person = {
    name: string;
    surname: string;
    age: number;
    hobbies: Object;
  };
  const [person, setPerson] = useState<Person[]>([]);

  function updatePerson() {
    // setPerson([{name: 'New', surname: 'AF', age: 55, hobbies: {}}]);
  }

  function addNewPerson() {
    console.log('Adding new person...');
    setPerson([
      ...person,
      {
        name: generateString(5),
        surname: generateString(5),
        age: 20,
        hobbies: {},
      },
    ]);
    console.log(person);
  }

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <View style={backgroundStyle}>
        <Header />
        <Button onPress={addNewPerson} title={'Add from react'} />
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
            flexDirection: 'column',
            padding: 20,
          }}>
          <PersonView
            person={person}
            updatePerson={updatePerson}
            addNewPerson={addNewPerson}
          />
        </View>
      </View>
    </SafeAreaView>
  );
}

function generateString(length: number) {
  const characters =
    'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let result = ' ';
  const charactersLength = characters.length;
  for (let i = 0; i < length; i++) {
    result += characters.charAt(Math.floor(Math.random() * charactersLength));
  }

  return result;
}

export default App;
