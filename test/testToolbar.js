/**
 * Created by alekhrycaiko on 2017-01-22.
 */
import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import Toolbar from '../scripts/js/toolbar.jsx';

describe('<Toolbar />', () => {
    it('renders Toolbar', () => {
        const wrapper = shallow(<Toolbar/>);
        expect(wrapper.find('.navbar')).to.have.length(1);
        expect(wrapper.find('.navbarText')).to.have.length(1);
        expect(wrapper.find('.navbarLinks')).to.have.length(1);
    });
});
