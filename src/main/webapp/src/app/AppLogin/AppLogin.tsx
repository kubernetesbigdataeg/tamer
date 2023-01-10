import React from 'react';
import {
  LoginFooterItem,
  LoginForm,
  LoginMainFooterBandItem,
  LoginPage,
  ListItem
} from '@patternfly/react-core';
import ExclamationCircleIcon from '@patternfly/react-icons/dist/js/icons/exclamation-circle-icon';
import brandImg from '!!url-loader!@app/bgimages/kubernetes-big-data-eg.svg';
import Cookies from 'js-cookie';

class AppLogin extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      showHelperText: false,
      usernameValue: '',
      isValidUsername: true,
      passwordValue: '',
      isValidPassword: true,
      isRememberMeChecked: false,
      alreadyLoged: false
    };

    this.handleUsernameChange = value => {
      this.setState({ usernameValue: value });
    };

    this.handlePasswordChange = passwordValue => {
      this.setState({ passwordValue });
    };

    this.onRememberMeClick = () => {
      this.setState({ isRememberMeChecked: !this.state.isRememberMeChecked });
    };

    this.parseJwt = (token) => {
      try {
        return JSON.parse(atob(token.split('.')[1]));
      } catch (e) {
        return null;
      }
    };

    this.onLoginButtonClick = event => {
      event.preventDefault();
      this.setState({ isValidUsername: !!this.state.usernameValue });
      this.setState({ isValidPassword: !!this.state.passwordValue });
      this.setState({ showHelperText: !this.state.usernameValue || !this.state.passwordValue });

      (async () => {
        const rawResponse =
          await fetch(window.location.protocol +
            "//" + window.location.hostname +
            ":8080" +
            "/api/v1/access/login", {
            method: 'POST',
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({
              "username": this.state.usernameValue,
              "password": this.state.passwordValue
            })
          });
        const content = await rawResponse.json();

        if (rawResponse.status == 200) {
          Cookies.set('tamer-auth', content);
          let json = this.parseJwt(Cookies.getJSON('tamer-auth').access_token);

          const rawResponse2 =
            await fetch(window.location.protocol +
              "//" + window.location.hostname +
              ":8080" +
              "/api/v1/access/info", {
              method: 'GET',
              headers: {
                'Authorization': `Bearer ${content.token}`,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              }
            });

          const group = await rawResponse2.json();
          Cookies.set('tamer-userinfo', group)

          this.props.handleLogin(true, json);
        } else {
          console.log("LOGIN failed");
        }
      })();
    };
  }

  public componentDidMount(): void {
    let value = {};
    value = Cookies.getJSON('tamer-auth');
    if (value) {
      this.setState({ alreadyLoged: true });
    } else {
      this.setState({ alreadyLoged: false});
    }
  }

  public render() {
    const helperText = (
      <React.Fragment>
        <ExclamationCircleIcon />
        &nbsp;Invalid login credentials.
      </React.Fragment>
    );

    const forgotCredentials = (
      <LoginMainFooterBandItem>
        <a href="#">Forgot username or password?</a>
      </LoginMainFooterBandItem>
    );

    const listItem = (
      <React.Fragment>
        <ListItem>
          <LoginFooterItem href="#">Terms of Use </LoginFooterItem>
        </ListItem>
        <ListItem>
          <LoginFooterItem href="#">Help</LoginFooterItem>
        </ListItem>
        <ListItem>
          <LoginFooterItem href="#">Privacy Policy</LoginFooterItem>
        </ListItem>
      </React.Fragment>
    );

    const loginForm = (
      <LoginForm
        showHelperText={this.state.showHelperText}
        helperText={helperText}
        usernameLabel="Username"
        usernameValue={this.state.usernameValue}
        onChangeUsername={this.handleUsernameChange}
        isValidUsername={this.state.isValidUsername}
        passwordLabel="Password"
        passwordValue={this.state.passwordValue}
        isShowPasswordEnabled
        onChangePassword={this.handlePasswordChange}
        isValidPassword={this.state.isValidPassword}
        rememberMeLabel="Keep me logged in for 30 days."
        isRememberMeChecked={this.state.isRememberMeChecked}
        onChangeRememberMe={this.onRememberMeClick}
        onLoginButtonClick={this.onLoginButtonClick}
      />
    );

    const images = {
      lg: '/assets/images/pfbg_1200.jpg',
      sm: '/assets/images/pfbg_768.jpg',
      sm2x: '/assets/images/pfbg_768@2x.jpg',
      xs: '/assets/images/pfbg_576.jpg',
      xs2x: '/assets/images/pfbg_576@2x.jpg'
    };

    return (
      this.state.alreadyLoged?
        false
        :
      <LoginPage
        footerListVariants="inline"
        brandImgSrc={brandImg}
        brandImgAlt="PatternFly logo"
        backgroundImgSrc={images}
        backgroundImgAlt="Images"
        footerListItems={listItem}
        textContent="This is placeholder text only. Use this area to place any information or introductory message about your application that may be relevant to users."
        loginTitle="Log in to your account"
        loginSubtitle="Please use mock server backend credentials users.json"
        forgotCredentials={forgotCredentials}>
        {loginForm}
      </LoginPage>
    )
  }
}

export { AppLogin };
