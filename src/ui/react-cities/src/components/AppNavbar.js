import React, { useState } from 'react';
import { Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink } from 'reactstrap';
import { Link } from 'react-router-dom';

const AppNavbar = (props) => {

  const [isOpen, setIsOpen] = useState(false);
  const isAuthenticated = sessionStorage.getItem("isAuthenticated");

  return (
    <Navbar color="dark" dark expand="md" sticky="top">
      <NavbarBrand tag={Link} to="/">CITIES</NavbarBrand>
      <NavbarToggler onClick={() => { setIsOpen(!isOpen) }} />
      <Collapse isOpen={isOpen} navbar>
        <Nav className="justify-content-end" style={{ width: "100%" }} navbar>
          <NavItem>
            { ({isAuthenticated} === "true") ? <NavLink href="/logout">Logout</NavLink> : <NavLink href="/login">Login</NavLink> }
          </NavItem>
        </Nav>
      </Collapse>
    </Navbar>
  );
};

export default AppNavbar;