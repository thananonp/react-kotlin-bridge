import React, {useEffect, useRef, useState} from 'react';
import {
  PixelRatio,
  UIManager,
  findNodeHandle,
  NativeEventEmitter,
  NativeModules,
} from 'react-native';

import {PersonViewManager} from './PersonViewManager';

const createFragment = viewId =>
  UIManager.dispatchViewManagerCommand(
    viewId,
    // we are calling the 'create' command
    UIManager.PersonViewManager.Commands.create.toString(),
    [viewId],
  );

export const PersonView = ({person, updatePerson, addNewPerson}) => {
  const ref = useRef(null);

  console.log('children', person);

  useEffect(() => {
    const viewId = findNodeHandle(ref.current);
    createFragment(viewId);
  }, []);

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter();
    let eventListener = eventEmitter.addListener('personOnPress', updatePerson);
    let addNewPersonListener = eventEmitter.addListener(
      'personAddNew',
      addNewPerson,
    );

    // Removes the listener once unmounted
    return () => {
      console.log('removed');
      eventListener.remove();
      addNewPersonListener.remove();
    };
  }, [person]);

  return (
    <PersonViewManager
      style={{
        // converts dpi to px, provide desired height
        height: PixelRatio.getPixelSizeForLayoutSize(300),
        // converts dpi to px, provide desired width
        width: PixelRatio.getPixelSizeForLayoutSize(200),
      }}
      ref={ref}
      persons={person}
    />
  );
};
