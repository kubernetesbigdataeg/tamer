import * as React from 'react';
import '@patternfly/react-core/dist/styles/base.css';
import { BrowserRouter as Router } from 'react-router-dom';
import { AppLayout } from '@app/AppLayout/AppLayout';
import { AppRoutes } from '@app/routes';
import '@app/app.css';
import { AppLogin } from '@app/AppLogin/AppLogin';
import Cookies from 'js-cookie';

const App: React.FunctionComponent = () => {
  const [isLoged, setIsLoged] = React.useState(false);

  const onHandleLogin = (value) => {
    setIsLoged(value);
  }

  React.useEffect(() => {
    let value = {};
    value = Cookies.getJSON('tamer-auth');
    if (value) {
      setIsLoged(true);
    }
  }, []);

  return (
    ! isLoged?
      <AppLogin handleLogin={onHandleLogin}/>
      :
      <Router>
        <AppLayout>
          <AppRoutes />
        </AppLayout>
      </Router>
  )
};

export default App;
