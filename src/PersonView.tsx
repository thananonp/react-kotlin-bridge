import React, {useEffect, useRef} from 'react';
import {PixelRatio, UIManager, findNodeHandle} from 'react-native';

import {PersonViewManager} from './PersonViewManager';

const createFragment = viewId =>
  UIManager.dispatchViewManagerCommand(
    viewId,
    // we are calling the 'create' command
    UIManager.PersonViewManager.Commands.create.toString(),
    [viewId],
  );

export const PersonView = () => {
  const ref = useRef(null);

  useEffect(() => {
    const viewId = findNodeHandle(ref.current);
    createFragment(viewId);
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
      persons={[
        {name: 'Bee', surname: 'Buzz', age: 24, hobbies: {}},
        {name: 'Ceca', surname: 'Culling', age: 25, hobbies: {}},
      ]}
    />
  );
};
