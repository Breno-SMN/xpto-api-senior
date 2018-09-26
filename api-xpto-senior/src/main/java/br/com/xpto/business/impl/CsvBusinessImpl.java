package br.com.xpto.business.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;



import br.com.xpto.business.CityBusiness;
import br.com.xpto.business.CsvBusiness;
import br.com.xpto.model.CityModel;
import br.com.xpto.model.FileModel;
import br.com.xpto.resource.exceptions.CsvDirectoryFailCreateException;
import br.com.xpto.resource.exceptions.CsvException;
import br.com.xpto.resource.exceptions.CsvUnsuportedException;

@Service
public class CsvBusinessImpl implements CsvBusiness {

	private final Path csvLocation;
	
	@Autowired
	private CityBusiness cityService;

	@Autowired
	public CsvBusinessImpl() {
		this.csvLocation = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.csvLocation);
		} catch (Exception ex) {
			throw new CsvDirectoryFailCreateException(new Object[] {ex.getMessage() });
		}
	}

	public FileModel storeFile(MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename().trim());
		try {
			
			// estoura exception se o arquivo passado for diferente de csv
			if (!file.getContentType().equals("text/csv")) {
				throw new CsvUnsuportedException(new Object[] {fileName });
			}

			// Copia o arquivo para o local setado
			Path targetLocation = this.csvLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			// Converter os dados do CSV em lista para salvar
			List<CityModel> cities = cityService.getCitiesByFile(file, this.csvLocation);

			
			List<CityModel> teste = cityService.saveAllCities(cities);
			
			
			// Deletar arquivo após salvar no banco em memoria
			Files.delete(this.csvLocation.resolve(file.getOriginalFilename().trim()).normalize());

			FileModel response = new FileModel(HttpStatus.ACCEPTED, "Salvo com sucesso",
					file.getContentType(), file.getSize());
			return response;
		} catch (Exception ex) {
			throw new CsvException(new Object[] { ex.getMessage() });
		}
	}

}
