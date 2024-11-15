package net.dries007.tfcthings.proxy;

public class CommonProxy {

  public void syncJavelinGroundState(int javelinID, boolean inGround) {
    throw new WrongSideException("Tried to sync hook javelin on the server");
  }

  class WrongSideException extends RuntimeException {

    public WrongSideException(final String message) {
      super(message);
    }

    public WrongSideException(final String message, final Throwable cause) {
      super(message, cause);
    }
  }

}
