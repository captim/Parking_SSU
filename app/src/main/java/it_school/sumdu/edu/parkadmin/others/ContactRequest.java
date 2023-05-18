package it_school.sumdu.edu.parkadmin.others;

import it_school.sumdu.edu.parkadmin.model.Car;
import it_school.sumdu.edu.parkadmin.model.Owner;
import it_school.sumdu.edu.parkadmin.model.Count;
import it_school.sumdu.edu.parkadmin.model.Bind;

import java.util.List;

public class ContactRequest {

    public interface OwnerRequest {
        void createOwner(Owner owner, Response<Boolean> response);
        void readOwner(int ownerId, Response<Owner> response);
        void readAllOwner(Response<List<Owner>> response);
        void updateOwner(Owner owner, Response<Boolean> response);
        void deleteOwner(int ownerId, Response<Boolean> response);
    }

    public interface CarRequest {
        void createCar(Car car, Response<Boolean> response);
        void readAllCar(Response<List<Car>> response);
        void updateCar(Car car, Response<Boolean> response);
        void deleteCar(int carId, Response<Boolean> response);
    }

    public interface BindRequest {
        void createBind(int ownerId, int carId, Response<Boolean> response);
        void readAllBindByOwner(int ownerId, Response<List<Car>> response);
        void readAllBind(int ownerId, Response<List<Bind>> response);
        void deleteBind(int ownerId, int carId, Response<Boolean> response);
    }

    public interface CountRequest {
        void getCount(Response<Count> response);
    }
}