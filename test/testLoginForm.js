/**
 * Created by alekhrycaiko on 2017-02-16.
 */
import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import LoginComponent from '../scripts/js/login.jsx';

describe('<Toolbar />', () => {
    it('renders Login Component', () => {
        const wrapper = shallow(<LoginComponent/>);
        expect(wrapper.find('FormGroup')).to.have.length(4);
        expect(wrapper.find('FormControl')).to.have.length(2);
    });
});
