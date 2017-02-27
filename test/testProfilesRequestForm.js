import React from 'react';
import { shallow } from 'enzyme';
import {expect} from 'chai';
import ProfilesRequestForm from '../scripts/js/profilerequestform.jsx';

describe('<ProfileRequestForm />', () => {
    it('renders Profiles Request Form', () => {
        const wrapper = shallow(<ProfilesRequestForm/>);
        expect(wrapper.find('.profilesResponse')).to.have.length(1);
        expect(wrapper.find('.profileResponse')).to.have.length(1);
        expect(wrapper.find('.description')).to.have.length(1);
        expect(wrapper.find('.submitBtn')).to.have.length(1);
    });
});
