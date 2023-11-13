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

export const PersonView = ({
  people,
  isRefreshing,
  selectPerson,
  addNewPerson,
  deleteAllPerson,
  refresh,
}) => {
  const ref = useRef(null);

  useEffect(() => {
    const viewId = findNodeHandle(ref.current);
    createFragment(viewId);
  }, []);

  useEffect(() => {
    const eventEmitter = new NativeEventEmitter();
    // Listen to Kotlin event here
    let eventListener = eventEmitter.addListener('personOnPress', event =>
      selectPerson(event),
    );
    let addNewPersonListener = eventEmitter.addListener(
      'personAddNew',
      addNewPerson,
    );

    let deleteAllPersonListener = eventEmitter.addListener(
      'deleteAllPerson',
      deleteAllPerson,
    );

    let refreshListener = eventEmitter.addListener('refresh', refresh);

    // Removes the listener once unmounted
    return () => {
      console.log('removed');
      eventListener.remove();
      addNewPersonListener.remove();
      deleteAllPersonListener.remove();
      refreshListener.remove();
    };
  }, [people]);

  return (
    <PersonViewManager
      style={{
        // converts dpi to px, provide desired height
        height: PixelRatio.getPixelSizeForLayoutSize(300),
        // converts dpi to px, provide desired width
        width: PixelRatio.getPixelSizeForLayoutSize(200),
      }}
      ref={ref}
      people={people}
      isRefreshing={isRefreshing}
    />
  );
};
