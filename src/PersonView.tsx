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

export const PersonView = ({person, updatePerson}) => {
  const ref = useRef(null);

  useEffect(() => {
    const viewId = findNodeHandle(ref.current);
    createFragment(viewId);

    const eventEmitter = new NativeEventEmitter();
    let eventListener = eventEmitter.addListener('personOnPress', updatePerson);

    // Removes the listener once unmounted
    return () => {
      eventListener.remove();
    };
  }, []);

  return (
    <PersonViewManager
      style={{
        // converts dpi to px, provide desired height
        height: PixelRatio.getPixelSizeForLayoutSize(200),
        // converts dpi to px, provide desired width
        width: PixelRatio.getPixelSizeForLayoutSize(200),
      }}
      ref={ref}
      persons={person}
    />
  );
};
