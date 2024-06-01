package com.example.quicknotes.controller;

import com.example.quicknotes.domain.dto.TaskDto;

import com.example.quicknotes.domain.entity.Role;
import com.example.quicknotes.domain.entity.User;
import com.example.quicknotes.repo.RoleRepository;
import com.example.quicknotes.repo.TaskManagerRepository;
import com.example.quicknotes.repo.UserRepository;
import com.example.quicknotes.security.sec_dto.TokenResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuickNotesAppTest {

	@LocalServerPort
	private int port;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TaskManagerRepository productRepository;

	private TestRestTemplate template;
	private HttpHeaders headers;
	private TaskDto testProduct;

	private String adminAccessToken;
	private String userAccessToken;

	private final String TEST_PRODUCT_TITLE = "Test product";
	private final BigDecimal TEST_PRODUCT_PRICE = new BigDecimal(777);
	private final String ADMIN_NAME = "Test Admin";
	private final String USER_NAME = "Test User";
	private final String TEST_PASSWORD = "Test password";
	private final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
	private final String USER_ROLE_NAME = "ROLE_USER";

	private final String URL_PREFIX = "http://localhost:";
	private final String AUTH_RESOURCE_NAME = "/auth";
	private final String PRODUCTS_RESOURCE_NAME = "/products";
	private final String LOGIN_ENDPOINT = "/login";
	private final String ACCESS_ENDPOINT = "/access";
	private final String ALL_ENDPOINT = "/all";
	private final String BEARER_PREFIX = "Bearer ";

	private final String AUTH_HEADER_NAME = "Authorization";

	@BeforeEach
	public void setUp() {
		template = new TestRestTemplate();
		headers = new HttpHeaders();

		testProduct = new TaskDto();
		testProduct.setTitle(TEST_PRODUCT_TITLE);


		BCryptPasswordEncoder encoder = null;
		Role roleAdmin;
		Role roleUser = null;

		User admin = userRepository.findByUsername(ADMIN_NAME);
		User user = userRepository.findByUsername(USER_NAME);

		if (admin == null) {
			encoder = new BCryptPasswordEncoder();
			roleAdmin = roleRepository.findByTitle(ADMIN_ROLE_NAME);
			roleUser = roleRepository.findByTitle(USER_ROLE_NAME);

			admin = new User();
			admin.setUsername(ADMIN_NAME);
			admin.setPassword(encoder.encode(TEST_PASSWORD));
			admin.setRoles(Set.of(roleAdmin, roleUser));

			userRepository.save(admin);
		}

		if (user == null) {
			encoder = encoder == null ? new BCryptPasswordEncoder() : encoder;

			user = new User();
			user.setUsername(USER_NAME);
			user.setPassword(encoder.encode(TEST_PASSWORD));
			user.setRoles(Set.of(roleUser == null ?
					roleRepository.findByTitle(USER_ROLE_NAME) : roleUser));

			userRepository.save(user);
		}

		admin.setPassword(TEST_PASSWORD);
		admin.setRoles(null);

		user.setPassword(TEST_PASSWORD);
		user.setRoles(null);

		String url = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;
		HttpEntity<User> request = new HttpEntity<>(admin, headers);

		ResponseEntity<TokenResponseDto> response = template
				.exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

		assertNotNull(response.getBody(), "Authorization response body is null");
		adminAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();

		request = new HttpEntity<>(user, headers);

		response = template
				.exchange(url, HttpMethod.POST, request, TokenResponseDto.class);

		assertNotNull(response.getBody(), "Authorization response body is null");
		userAccessToken = BEARER_PREFIX + response.getBody().getAccessToken();
	}

	@Test
	public void positiveGettingAllProductsWithoutAuthorization() {

		String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME + ALL_ENDPOINT;
		HttpEntity<Object> request = new HttpEntity<>(headers);

		ResponseEntity<Object> response = template
				.exchange(url, HttpMethod.GET, request, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status");
		assertTrue(response.hasBody(), "Response does not contain body");
	}

	@Test
	public void negativeSavingProductWithoutAuthorization() {

		String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
		HttpEntity<TaskDto> request = new HttpEntity<>(testProduct, headers);

		ResponseEntity<TaskDto> response = template
				.exchange(url, HttpMethod.POST, request, TaskDto.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		assertFalse(response.hasBody());
	}

	@Test
	public void positiveSavingProductWithAdminAuthorization() {

		String url = URL_PREFIX + port + PRODUCTS_RESOURCE_NAME;
		headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));
		HttpEntity<TaskDto> request = new HttpEntity<>(testProduct, headers);

		ResponseEntity<TaskDto> response = template
				.exchange(url, HttpMethod.POST, request, TaskDto.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		TaskDto savedProduct = response.getBody();
		assertNotNull(savedProduct);
		assertEquals(TEST_PRODUCT_TITLE, savedProduct.getTitle());
		assertNotNull(savedProduct.getId());
	}
}