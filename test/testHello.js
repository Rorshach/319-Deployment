/**
 * Created by alekhrycaiko on 2017-01-22.
 */
import jsdom from 'jsdom'

import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import Hello from '../scripts/js/hello.jsx';

describe('<Hello />', () => {
    it('renders Hello', () => {
        const wrapper = shallow(<Hello/>);
        console.log(wrapper.props());
        expect(wrapper.props().handleSomeProp).to.be.defined;
    });
});