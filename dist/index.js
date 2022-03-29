import * as React from 'react';
import { requireNativeComponent, StyleSheet } from 'react-native';
const MapboxNavigationWalking = (props) => {
    return <RNMapboxNavigation style={styles.container} {...props}/>;
};
const RNMapboxNavigation = requireNativeComponent('MapboxNavigationWalking', MapboxNavigationWalking);
const styles = StyleSheet.create({
    container: {
        flex: 1,
    },
});
export default MapboxNavigationWalking;
