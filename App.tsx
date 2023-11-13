/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React, {useState} from 'react';
import {
  Button,
  SafeAreaView,
  StatusBar,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import {Colors, Header} from 'react-native/Libraries/NewAppScreen';
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
  const [people, setPeople] = useState<Person[]>([
    {name: 'Default', surname: 'Person', age: 20, hobbies: {}},
  ]);
  const [refreshing, setRefreshing] = useState(false);

  function selectPerson(person: Person) {
    console.log(person);
    // Use person here
  }

  function addNewPerson() {
    console.log('Adding new person...');
    setPeople([
      ...people,
      {
        name: generateString(5),
        surname: generateString(5),
        age: 20,
        hobbies: {},
      },
    ]);
    console.log(people);
  }

  async function deleteAllPerson() {
    await new Promise(r => setTimeout(r, 2000));
    setPeople([]);
  }

  async function refresh() {
    setRefreshing(true);
    await new Promise(r => setTimeout(r, 2000));
    setRefreshing(false);
    setPeople([{name: 'Default', surname: 'Person', age: 20, hobbies: {}}]);
  }

  return (
    <SafeAreaView style={backgroundStyle}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor={backgroundStyle.backgroundColor}
      />
      <View style={backgroundStyle}>
        <Header />
        {/* <Button onPress={addNewPerson} title={'Add from react'} /> */}
        <Text>{refreshing.toString()}</Text>
        <View
          style={{
            backgroundColor: isDarkMode ? Colors.black : Colors.white,
            flexDirection: 'column',
            padding: 20,
          }}>
          <PersonView
            // source of truth
            people={people}
            isRefreshing={refreshing}
            // function callbacks
            selectPerson={selectPerson}
            addNewPerson={addNewPerson}
            deleteAllPerson={deleteAllPerson}
            refresh={refresh}
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
