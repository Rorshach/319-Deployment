/**
 * Created by alekhrycaiko on 2017-02-16.
 */
import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import LoginComponent from '../scripts/js/tabs.jsx';

describe('<Toolbar />', () => {
    it('renders Login Component', () => {
        const wrapper = shallow(<LoginComponent/>);
        expect(wrapper.find('Tab')).to.have.length(3);
    });
});
