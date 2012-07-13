package ar.unq.tpi.nny.pacman.domain;

public enum Direction {
	UP("up"), DOWN("down"), LEFT("left"), RIGHT("right"), STOP("stop"), NO("no");

	private final String directionName;

	Direction(final String directionName) {
		this.directionName = directionName;
	}

	public static final Direction getOppositeDirection(
			final Direction currentDirection) {
		if (currentDirection == Direction.UP) {
			return Direction.DOWN;
		} else if (currentDirection == Direction.DOWN) {
			return Direction.UP;
		} else if (currentDirection == Direction.LEFT) {
			return Direction.RIGHT;
		} else if (currentDirection == Direction.RIGHT) {
			return Direction.LEFT;
		} else if (currentDirection == Direction.STOP) {
			return Direction.STOP;
		} else {
			return Direction.NO;
		}
	}

	public final String toString() {
		return directionName;
	}
}
