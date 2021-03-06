package br.ce.wcaquino.tasksfrontend.controller;

import java.time.LocalDate;


import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.controller.TaskController;
import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TasksControllerTest {
	
	@Mock
	private TaskRepo taskRepo;
	
	
	@InjectMocks
	private TaskController controller ;
	
	@org.junit.Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Nao deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}

	}

	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setTask("Descricao");
		try {
			controller.save(todo);
			Assert.fail("Nao deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}

	}

	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.of(2020, 01, 01));
		try {
			controller.save(todo);
			Assert.fail("Nao deveria chegar nesse ponto");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}

	}

	@Test
	public void deveSalvarTarefaComSucceso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descricao");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		
		Mockito.verify(taskRepo).save(todo);
		

	}

}
