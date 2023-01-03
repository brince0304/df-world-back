import './App.css';
import React from 'react';
import {useState} from "react";
import {BrowserRouter, Routes, Route, Link} from "react-router-dom";
import Login from "./Login/Login";
import Main from "./Main/Main";
import Header from "./Header/Header";
import NotFound from "./NotFound/NotFound";


function App() {
    let [message, setMessage] = useState([]);
    if (sessionStorage.getItem("isAuthenticated") == null) {
        message = "HOMEPAGE";
    }

    return (
        <BrowserRouter>
                <Header/>
                <Routes>
                    <Route path="/" element={<Main/>}></Route>
                    <Route path="/login" element={<Login/>}></Route>
                    {/* 상단에 위치하는 라우트들의 규칙을 모두 확인, 일치하는 라우트가 없는경우 처리 */}
                    <Route path="*" element={<NotFound/>}></Route>
                </Routes>

        </BrowserRouter>
    );
}
export default App;