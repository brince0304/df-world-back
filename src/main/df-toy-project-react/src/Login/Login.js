import React, {useState} from 'react';
import './Login.css';
import axios from "axios";






const Login = props => (
    <LoginForm />
);

function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

        return(
            <div id="loginform">
                <FormHeader title="Login" />
                <Form />
                <OtherMethods />
            </div>
        )

}

const FormHeader = props => (
    <h2 id="headerTitle">{props.title}</h2>
);


const Form = props => (
    <div>
        <FormInput _onChange={(e)=>{
            setUsername(e.target.value);

        } description="Username" placeholder="Enter your username" type="text" />
        <FormInput description="Password" placeholder="Enter your password" type="password"/>
        <FormButton title="Log in"/>
    </div>
);

const FormButton = props => (
    <div id="button" className="row">
        <button>{props.title}</button>
    </div>
);

const FormInput = props => (
    <div className="row">
        <label>{props.description}</label>
        <input type={props.type} placeholder={props.placeholder}/>
    </div>
);

const OtherMethods = props => (
    <div id="alternativeLogin">
        <label>Or sign in with:</label>
        <div id="iconGroup">
            <Facebook />
            <Twitter />
            <Google />
        </div>
    </div>
);

const Facebook = props => (
    // eslint-disable-next-line jsx-a11y/anchor-has-content,jsx-a11y/anchor-is-valid
    <a href="#" id="facebookIcon"></a>
);

const Twitter = props => (
    // eslint-disable-next-line jsx-a11y/anchor-has-content,jsx-a11y/anchor-is-valid
    <a href="#" id="twitterIcon"></a>
);

const Google = props => (
    // eslint-disable-next-line jsx-a11y/anchor-has-content,jsx-a11y/anchor-is-valid
    <a href="#" id="googleIcon"></a>
);


export default Login;
