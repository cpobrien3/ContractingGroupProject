/**
 * @author Andrew Pierce - ajpierce1
 * CIS175 - Fall 2021
 * Nov 13, 2021
 */
package dmacc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dmacc.model.Contract;
import dmacc.model.Employee;
import dmacc.model.Equipment;
import dmacc.model.User;
import dmacc.repository.ContractRepository;
import dmacc.repository.EmployeeRepository;
import dmacc.repository.EquipmentRepository;
import dmacc.repository.UserRepository;

/**
 * @author Andrew Pierce - ajpierce1
 */

/*
 * Things to do:
 * 
 * Contract -view -input -delete -update -edit
 * 
 */

@Controller
@RequestMapping("/")
public class WebController {

	@Autowired
	UserRepository userRepo;
	@Autowired
	EquipmentRepository equipmentRepo;
	@Autowired
	ContractRepository contractRepo;
	@Autowired
	EmployeeRepository employeeRepo;
	
	@Secured("ROLE_ADMIN")
	@GetMapping({ "/mainMenu", "/admin/mainMenu" })
	public String mainMenu() {
		return "mainMenu";
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping({ "/customerMenu", "/user/customerMenu" })
	public String customerMenu() {
		return "customerMenu";
	}

	// Users
	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping({ "/viewUsers", "/admin/viewUsers" })
	public String viewUsers(Model model) {
		if (userRepo.findAll().isEmpty()) {
			return addNewUser(model);
		}
		model.addAttribute("newUser", userRepo.findAll());
		return "viewUsers";
	}

	// @Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping({ "/inputUser", "/admin/inputUser" })
	private String addNewUser(Model model) {
		User u = new User();
		model.addAttribute("newUser", u);
		return "inputUser";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		userRepo.delete(u);
		return viewUsers(model);
	}

	@GetMapping("/editUser/{id}")
	public String findUserToUpdate(@PathVariable("id") int id, Model model) {
		User u = userRepo.findById(id).orElse(null);
		model.addAttribute("newUser", u);
		return "inputUser";
	}

	@PostMapping("/updateUser/{id}")
	public String editUser(User u, Model model) {
		userRepo.save(u);
		return viewUsers(model);
	}

	// Contracts
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@GetMapping("/viewContracts")
	public String viewContracts(Model model) {
		if (contractRepo.findAll().isEmpty()) {
			return addNewContract(model);
		}
		model.addAttribute("newContract", contractRepo.findAll());
		return "viewContracts";
	}

	@GetMapping("/inputContract")
	private String addNewContract(Model model) {
		Contract c = new Contract();
		model.addAttribute("newContract", c);
		return "inputContract";
	}

	@GetMapping("/deleteContract/{id}")
	public String deleteContract(@PathVariable("id") int id, Model model) {
		Contract c = contractRepo.findById(id).orElse(null);
		contractRepo.delete(c);
		return viewContracts(model);
	}

	@GetMapping("/editContract/{id}")
	public String findContractToUpdate(@PathVariable("id") int id, Model model) {
		Contract c = contractRepo.findById(id).orElse(null);
		model.addAttribute("newContract", c);
		return "inputContract";
	}

	@PostMapping("/updateContract/{id}")
	public String editContract(Contract c, Model model) {
		contractRepo.save(c);
		return viewContracts(model);
	}

	// Equipment
	@Secured("ROLE_ADMIN")
	@GetMapping("/viewEquipment")
	public String viewEquipment(Model model) {
		if (equipmentRepo.findAll().isEmpty()) {
			return addNewEquipment(model);
		}
		model.addAttribute("newEquipment", equipmentRepo.findAll());
		return "viewEquipment";
	}

	@GetMapping("/inputEquipment")
	public String addNewEquipment(Model model) {
		Equipment e = new Equipment();
		model.addAttribute("newEquipment", e);
		return "inputEquipment";
	}

	@GetMapping("/deleteEquipment/{id}")
	public String deleteEquipment(@PathVariable("id") int id, Model model) {
		Equipment e = equipmentRepo.findById(id).orElse(null);
		equipmentRepo.delete(e);
		return viewEquipment(model);
	}

	@GetMapping("/editEquipment/{id}")
	public String findEquipmentToUpdate(@PathVariable("id") int id, Model model) {
		Equipment e = equipmentRepo.findById(id).orElse(null);
		model.addAttribute("newEquipment", e);
		return "inputEquipment";
	}

	@PostMapping("/updateEquipment/{id}")
	public String editEquipment(Equipment e, Model model) {
		equipmentRepo.save(e);
		return viewEquipment(model);
	}

	// Status
	@GetMapping("/inputStatus")
	public String addNewEquipmentStatus(Model model) {
		Equipment e = new Equipment();
		model.addAttribute("newEquipment", e);
		return "inputStatus";
	}

	@GetMapping("/editEquipmentStatus/{id}")
	public String findStatusToUpdate(@PathVariable("id") int id, Model model) {
		Equipment e = equipmentRepo.findById(id).orElse(null);
		model.addAttribute("newEquipment", e);
		return "inputStatus";
	}

	@GetMapping("/viewEquipmentStatus")
	public String viewEquipmentStatus(Model model) {
		if (equipmentRepo.findAll().isEmpty()) {
			return addNewEquipment(model);
		}
		model.addAttribute("newEquipment", equipmentRepo.findAll());
		return "viewEquipmentStatus";
	}

	@PostMapping("/updateEquipmentStatus/{id}")
	public String editEquipmentStatus(Equipment e, Model model) {
		equipmentRepo.save(e);
		return viewEquipmentStatus(model);
	}
	
	@GetMapping("/inputEmployee")
	public String addNewEmployee(Model model) {
		Employee e = new Employee();
		model.addAttribute("newEmployee", e);
		return "inputEmployee";
	}

	@GetMapping("/editEmployee/{id}")
	public String findEmployeeToUpdate(@PathVariable("id") int id, Model model) {
		Employee e = employeeRepo.findById(id).orElse(null);
		model.addAttribute("newEmployee", e);
		return "inputEmployee";
	}

	@GetMapping("/viewEmployees")
	public String viewEmployee(Model model) {
		if (employeeRepo.findAll().isEmpty()) {
			return addNewEmployee(model);
		}
		model.addAttribute("newEmployee", employeeRepo.findAll());
		return "viewEmployees";
	}

	@PostMapping("/updateEmployee/{id}")
	public String editEmployee(Employee e, Model model) {
		employeeRepo.save(e);
		return viewEmployee(model);
	}
	

}
