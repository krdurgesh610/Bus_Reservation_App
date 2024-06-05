import Dropdown from 'react-bootstrap/Dropdown';

function Dropdown1() {
  return (
    <Dropdown>
      <Dropdown.Toggle variant="success" id="dropdown-basic">
        Dropdown Button
      </Dropdown.Toggle>

      <Dropdown.Menu>
        <Dropdown.Item href="/adminhomepage/addbus">Add Bus</Dropdown.Item>
        <Dropdown.Item href="#/action-2">Bus List</Dropdown.Item>
        <Dropdown.Item href="#/action-3">Edit Profile</Dropdown.Item>
        <Dropdown.Item href="#/action-4">Logout</Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
}

export default Dropdown1;
