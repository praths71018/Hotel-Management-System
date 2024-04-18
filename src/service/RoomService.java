package service;

import model.Room;
import java.util.List;

public class RoomService {
    public boolean checkIn(int roomNumber) {
        Room room = Room.getRoomByNumber(roomNumber);
        if (room != null && !room.isBooked()) {
            room.setBookedStatus(true);
            room.updateRoomStatusInDatabase();
            return true;
        }
        return false;
    }

    public void checkOut(int roomNumber) {
        Room room = Room.getRoomByNumber(roomNumber);
        if (room != null && room.isBooked()) {
            room.setBookedStatus(false);
            room.updateRoomStatusInDatabase();
        }
    }

    public boolean isRoomAvailable(int roomNumber) {
        Room room = Room.getRoomByNumber(roomNumber);
        return room != null && !room.isBooked();
    }

    public List<Room> getAllAvailableRooms() {
        return Room.getAllAvailableRooms();
    }
}
