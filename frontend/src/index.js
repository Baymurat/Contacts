import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import {addLocaleData, IntlProvider} from 'react-intl';

import en from 'react-intl/locale-data/en';
import ru from 'react-intl/locale-data/ru';

import {flattenMessages} from "./utils";
import messages from './messages';

addLocaleData([...en, ...ru]);

let locale = (navigator.languages && navigator.languages[0])
    || navigator.language
    || 'en-US';

ReactDOM.render(
    <IntlProvider locale={locale} messages={flattenMessages(messages[locale])}>
        <App/>
    </IntlProvider>
    , document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
